/*
   Copyright 2011 m6d.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.m6d.filecrush.crush;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Reader;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.util.ToolRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.m6d.filecrush.crush.Crush;

/**
 * Dfs block size will be set to 50 and threshold set to 20%.
 */
@SuppressWarnings("deprecation")
public class CrushStandAloneSequenceFileTest {
	@Rule
	public final TemporaryFolder tmp = new TemporaryFolder();

	private JobConf job;

	@Before
	public void setup() throws Exception {
		job = new JobConf(false);

		job.set("fs.default.name", "file:///");
		job.set("fs.file.impl", "org.apache.hadoop.fs.LocalFileSystem");
		job.setLong("dfs.block.size", 50);
	}

	/**
	 * Crush creates a subdirectory in tmp to store all its transient data. Since this test uses the local file system, the present
	 * working directory is the parent of tmp. We delete it here since it's not so useful to clutter the build directory with
	 * empty directories.
	 */
	@After
	public void deleteTmp() throws IOException {
		File tmp = new File("tmp");

		if (tmp.exists()) {
			assertThat(tmp.delete(), is(true));
		}
	}

	@Test
	public void standAloneOutput() throws Exception {

		File in = tmp.newFolder("in");

		createFile(in, "skipped-0", 0, 25);
		createFile(in, "skipped-1", 1, 25);
		createFile(in, "skipped-2", 2, 25);
		createFile(in, "skipped-3", 3, 25);

		File subdir = tmp.newFolder("in/subdir");

		createFile(subdir, "lil-0", 0, 1);
		createFile(subdir, "lil-1", 1, 2);
		createFile(subdir, "big-2", 2, 5);
		createFile(subdir, "big-3", 3, 5);

		File subsubdir = tmp.newFolder("in/subdir/subsubdir");

		createFile(subsubdir, "skipped-4", 4, 25);
		createFile(subsubdir, "skipped-5", 5, 25);

		File out = new File(tmp.getRoot(), "out");

		ToolRunner.run(job, new Crush(), new String[] {
				subdir.getAbsolutePath(), out.getAbsolutePath()
		});

		/*
		 * Make sure the original files are still there.
		 */
		verifyFile(in, "skipped-0", 0, 25);
		verifyFile(in, "skipped-1", 1, 25);
		verifyFile(in, "skipped-2", 2, 25);
		verifyFile(in, "skipped-3", 3, 25);

		verifyFile(subdir, "lil-0", 0, 1);
		verifyFile(subdir, "lil-1", 1, 2);
		verifyFile(subdir, "big-2", 2, 5);
		verifyFile(subdir, "big-3", 3, 5);

		verifyFile(subsubdir, "skipped-4", 4, 25);
		verifyFile(subsubdir, "skipped-5", 5, 25);

		/*
		 * Verify the crush output.
		 */
		verifyCrushOutput(out, new int[] { 0, 1 }, new int[] { 1, 2}, new int[] { 2, 5 }, new int[] { 3, 5 });
	}

	@Test
	public void noFiles() throws Exception {
		File in = tmp.newFolder("in");

		File out = new File(tmp.getRoot(), "out");

		ToolRunner.run(job, new Crush(), new String[] {
				in.getAbsolutePath(), out.getAbsolutePath()
		});

		assertThat(out.exists(), is(false));
	}

	private void verifyCrushOutput(File crushOutput, int[]... keyCounts) throws IOException {

		List<String> actual = new ArrayList<String>();

		Text text = new Text();
		IntWritable value = new IntWritable();

		Reader reader = new Reader(FileSystem.get(job), new Path(crushOutput.getAbsolutePath()), job);

		while (reader.next(text, value)) {
			actual.add(format("%s\t%d", text, value.get()));
		}

		reader.close();

		int expLines = 0;
		List<List<String>> expected = new ArrayList<List<String>>();


		for (int[] keyCount : keyCounts) {
			int key  = keyCount[0];
			int count = keyCount[1];

			List<String> lines = new ArrayList<String>();
			expected.add(lines);

			for (int i = 0, j = 0; i < count; i++, j = j == 9 ? 0 : j + 1) {
				String line = format("%d\t%d", key, j);
				lines.add(line);
			}

			expLines += count;
		}

		/*
		 * Make sure each file's data is contiguous in the crush output file.
		 */
		for (List<String> list : expected) {
			int idx = actual.indexOf(list.get(0));

			assertThat(idx, greaterThanOrEqualTo(0));

			assertThat(actual.subList(idx, idx + list.size()), equalTo(list));
		}

		assertThat(actual.size(), equalTo(expLines));
	}

	private void createFile(File dir, String fileName, int key, int count) throws IOException {
		File file = new File(dir, fileName);

		Writer writer = SequenceFile.createWriter(FileSystem.get(job), job, new Path(file.getAbsolutePath()), Text.class, IntWritable.class);

		Text text = new Text(Integer.toString(key));
		IntWritable value = new IntWritable();

		for (int i = 0, j = 0; i < count; i++, j = j == 9 ? 0 : j + 1) {
			value.set(j);

			writer.append(text, value);
		}

		writer.close();
	}

	private void verifyFile(File dir, String fileName, int key, int count) throws IOException {
		File file = new File(dir, fileName);

		Reader reader = new Reader(FileSystem.get(job), new Path(file.getAbsolutePath()), job);

		int i = 0;
		int actual = 0;

		Text text = new Text();
		IntWritable value = new IntWritable();

		while (reader.next(text, value)) {
			assertThat(text.toString(), equalTo(Integer.toString(key)));
			assertThat(value.get(), equalTo(i));

			if (i == 9) {
				i = 0;
			} else {
				i++;
			}

			actual++;
		}

		reader.close();

		assertThat(actual, equalTo(count));
	}
}
