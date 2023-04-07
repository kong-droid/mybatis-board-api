REST-API
==========

1. 작업 기간
``````````````````
2022.12 ~ 2022.02
``````````````````
2. DNS
```````````````````````````````````````````````````
API : https://api.kong-droid.site/swagger-ui.html
FRONT : https://kong-droid.site
````````````````````````````````````````````````````
3. SKILL
````````````````````````````````````````````````````
language : java
framework : spring boot, mybatis
build : gradle
````````````````````````````````````````````````````
4. 배포 및 실행
```````````````````````````````````````````````````````````````````````````````````````````````````````````````
운영 : Dspring.profiles.active=prod ( jar 압축 시... java -jar -Dspring.profiles.active=prod jar rest-api.jar
로컬 : (이클립스 기준)Run >configuration 에 들어가서 profile을 local 설정, Dspring.profiles.active=local
```````````````````````````````````````````````````````````````````````````````````````````````````````````````
5. [front github link](https://github.com/kong-droid/test-front, "FRONT")
6. 서버 플로우
<img alt="서버 플로우" src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fd28a831b-05f1-41f9-b9f5-d40520fe7f14%2FUntitled.png?id=6cab6f80-9a14-4e69-81b3-c53ebb903b64&table=block&spaceId=714a1395-88eb-4758-b4a6-77e8c9bf0344&width=2000&userId=96df505d-e86f-497d-a8e4-526b7c5ecea8&cache=v2"/>


