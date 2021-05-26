import pymysql
import sys

sys.path.append('../')

from my_settings import DATABASES

def connect_db():
    db = pymysql.connect(
             user='ssafy',
             password='ssafy',
             host='localhost',
             db='formychild',
             charset='utf8'
             )
    return db