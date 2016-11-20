import re, os, fnmatch

def fix_file_contents(filename):
    with open(filename, "r") as file:
        data = file.read()
    reg = 'actuallyadditions:[a-z]+\/([a-zA-z]+)'
    for found in re.findall(reg, data):
        data = data.replace(found, rename(found))
    reg = 'actuallyadditions:([a-zA-z]+)'
    for found in re.findall(reg, data):
        data = data.replace(found, rename(found))
    with open(filename, "w") as file:
        file.write(data)

def rename(oldName):
    reg = '[A-Z]?[a-z.]+'
    parts = re.findall(reg, oldName)
    newString = parts[0]
    for part in parts[1:]:
        newString += "_" + part.lower()
    print(newString)
    return newString

if __name__ == "__main__":
    cwd = os.getcwd()
    for subdir, dirs, files in os.walk(cwd):
        for file in files:
            if fnmatch.fnmatch(file, "*.png"):
                os.rename(os.path.join(subdir, file), os.path.join(subdir, rename(file)))