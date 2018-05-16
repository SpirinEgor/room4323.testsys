from flask import Flask, request
import json
import subprocess
import os
app = Flask(__name__)

@app.route('/', methods=['POST'])
def check():
    with open('share/data.json', 'w') as data:
        data.write(json.dumps(request.get_json()))

    problems_folder = '/home/voudy/Documents/room4323.testsys/docker/problems'
    share_folder = os.path.join(os.getcwd(), 'share')
    command = 'docker run -v {}:/home/problems -v {}:/home/share --rm testsys'.format(
                    problems_folder,
                    share_folder
                )
    subprocess.call(command, shell=True)
    with open('share/result.json', 'r') as res:
        result = res.read()
    subprocess.call('rm -f {}/*'.format(share_folder), shell=True)
    return result

@app.route('/', methods=['GET'])
def status():
    return json.dumps({'status': 'OK'})