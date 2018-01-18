import subprocess
from datetime import datetime, timedelta


def run_cmd(cmd_str, catch_output=False):
    print("kmsg: going to run: %s" % cmd_str)
    cmd_list = cmd_str.split(" ")
    sub = subprocess.Popen(cmd_list, stdout=subprocess.PIPE, stderr=subprocess.PIPE) \
        if catch_output else subprocess.Popen(cmd_list)
    (stdout, stderr) = sub.communicate()
    ret = sub.returncode
    return stdout, stderr, ret


def hdfs_dir_exist(d):
    check_dir = "hadoop fs -test -d {dir}".format(dir=d)
    return run_cmd(check_dir)[2] == 0


def run_func_during(func, start, end):
    dt = start
    while dt < end:
        func(dt)
        dt_time = datetime.strptime(dt, "%Y/%m/%d")
        dt_time = dt_time + timedelta(days=1)
        dt = dt_time.strftime("%Y/%m/%d")


if __name__ == "__main__":
    cmd = "ls"
    run_cmd(cmd)
