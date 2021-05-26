# my_settings.py

DATABASES = {
    'default' : {
        'ENGINE': 'django.db.backends.mysql', # 엔진
        'NAME': 'formychild', # MYDQL DB 이름
        'USER': 'ssafy', # 계정
        'PASSWORD': 'ssafy', # 비번
        'HOST': 'localhost', # 주소
        'PORT': '3306', # 포트번호
    }
}

SECRET_KEY = 's5798qsdaj@f!q%fdr0(!6&vc*m1))*4fa23^af80%g@rpm!p)'