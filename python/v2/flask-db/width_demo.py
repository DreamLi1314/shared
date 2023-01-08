# encoding=utf-8

class opened(object):
    def __init__(self, filename):
        self.handle = open(filename)
        print("Resource:%s" % filename)

    def __enter__(self):
        print("[enter%s]: Allocate resource." % self.handle)
        return self.handle  # 可以返回不同的对象

    def __exit__(self, exc_type, exc_value, exc_trackback):
        print("[Exit %s]: Free resource." % self.handle)
        if exc_trackback is None:
            print("[Exit %s]:Exited without exception." % self.handle)
            self.handle.close()
        else:
            print("[Exit %s]: Exited with exception raised." % self.handle)
        return False  # 可以省略，缺省的None也是被看做是False

with opened(r'/Users/dreamli/Library/Mobile Documents/com~apple~CloudDocs/MyFile/temp/Test03.java') as fp:
    for line in fp.readlines():
        print(line)
