import json
import shutil
import subprocess
import os

infoIn = open('/home/share/data.json', 'r')
infoOut = open('/home/share/result.json', 'w')
data = json.load(infoIn)
infoIn.close()  # Open input & output files

language = data['language']

# Write code to file
if language == 'C++':
    solution = open('solution.cpp', 'w')
    solution.write(data['code'])
    solution.close()
else:
    result = {
        'status': 'error',
        'message': 'no such language'
    }
    json.dump(result, infoOut)
    infoOut.close()
    exit(0)

problems_dir = '/home/problems/'
link_task = os.path.join(problems_dir, data['path_to_task'])
link_tests = os.path.join(link_task, 'tests')
time_limit = data['time_limit']
memory_limit = data['memory_limit']

tests = {}
test_number = 1
result = {
    'status': 'OK',
    'result': {
        'verdict': 'OK',
        'message': '',
        'tests': []
    }
}

for test in os.listdir(link_tests):  # Add test file, compile, check
        if len(test) == 2:
            answer = test + '.a'

            shutil.copyfile(os.path.join(link_tests, test),
                            os.path.join(os.getcwd(), test))
            shutil.copyfile(os.path.join(link_tests, answer),
                            os.path.join(os.getcwd(), answer))
            shutil.copyfile(os.path.join(link_task, 'check.cpp'),
                            os.path.join(os.getcwd(), 'check.cpp'))

            os.rename(test, 'sum.in')
            os.rename(answer, 'answer')

            # Compile solution
            subprocess.call('g++ solution.cpp > compilation.txt 2>&1', shell=True)

            compilation = open('compilation.txt', 'r')
            compilation_result = compilation.read()
            compilation.close()

            # Handle compilation error
            if 'error' in compilation_result:
                result['result']['verdict'] = 'CE'
                result['result']['message'] = compilation_result
                break

            # Run code
            subprocess.call('./a.out', shell=True)
            
            # Run checker
            subprocess.call('g++ --std=c++17 check.cpp', shell=True)
            subprocess.call('./a.out sum.in sum.out answer > verdict.txt 2>&1', shell=True)

            # Read result
            verdict_file = open('verdict.txt', 'r')
            verdict = verdict_file.read()
            verdict_file.close()

            # Save to result
            result['result']['tests'].append({
                test_number: verdict
            })

            test_number += 1
            if 'ok' not in verdict:
                result['result']['verdict'] = 'WA'
                break

json.dump(result, infoOut)
infoOut.close()