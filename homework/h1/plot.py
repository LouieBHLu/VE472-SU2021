import matplotlib.pyplot as plt
import pandas as pd

df = pd.read_excel('monitor.csv')
cpu = df['CPU']
ram = df['RAM']

# plt.title('')
plt.plot(cpu, 'g', label='CPU Percentage')
plt.plot(ram, 'b', label='RAM Percentage')

plt.legend(loc='best')
plt.savefig('usage.png')
plt.show()