# import numpy , get all numbers from command line and find sum and print it to std out

import sys
import numpy as np

if __name__ == '__main__':
    numbers = [int(x) for x in sys.argv[1:]]
    print(np.sum(numbers))