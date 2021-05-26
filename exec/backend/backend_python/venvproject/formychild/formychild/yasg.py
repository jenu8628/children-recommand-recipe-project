from django.conf.urls import url
from django.urls import path, include
from drf_yasg.views import get_schema_view
from rest_framework.permissions import AllowAny, IsAuthenticated, BasePermission
from drf_yasg import openapi

schema_url_patterns = [
    path('myapp/', include('myapp.urls')),
    path('recommendation/', include('recommendation.urls'))
]

schema_view = get_schema_view(
    openapi.Info(
        title="swagger",
        default_version = 'v1',
        description =
        '''
        오구오구 우리아이
        Open Api 문서 페이지
        ''',
    ),
    validators=['flex'],
    public=True,
    permission_classes=(AllowAny,),
    patterns=schema_url_patterns,
)