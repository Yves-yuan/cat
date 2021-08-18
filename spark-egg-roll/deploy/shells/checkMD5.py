import hashlib
import sys
from base64 import encode


def calculate_s3_etag(file_path, chunk_size=32 * 1024 * 1024):
    md5s = []
    with open(sys.argv[1], 'rb') as fp:

        while True:

            data = fp.read(int(sys.argv[2]))

            if not data:

                break

            md5s.append(hashlib.md5(data))



    if len(md5s) == 1:

        return '"{}"'.format(md5s[0].hexdigest())



    digests = b''.join(m.digest() for m in md5s)
    # print(digests)
    digests_md5 = hashlib.md5(digests)

    return '"{}-{}"'.format(digests_md5.hexdigest(), len(md5s))

if __name__ == '__main__':
    str=calculate_s3_etag(sys.argv[1],sys.argv[2])
    print(str)


