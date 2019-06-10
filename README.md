# flask based scrap news 
featured:
    1. scrapt news by keyword in 3 news 
    2. using firebase with flask 
    3. response just ok 200 but data saved to firestore

# how to use 
1. install python first 
2. install pip (windows in another case don't use to install pip)
3. install virtualenv using this command ``` python3 -m pip install virtualenv ```
4. create virtualenvirontmend using this command ``` python3 -m virtualenv <your virtualenv name>```
5. activate it on windows using command ``` \<your virtualenv name>\Scripts\activate``` and linux ```source <your virtualenv name>/bin/activate```
6. install all requirement using command ``` pip install -r req.txt```
7. export your firebase json to env using command ```export GOOGLE_APPLICATION_CREDENTIALS=<your services.json>```
8. all have ready and you can run with command ```python app.py```

#in production mode
please follow this tutorial https://www.digitalocean.com/community/tutorials/how-to-serve-flask-applications-with-uwsgi-and-nginx-on-ubuntu-14-04 or https://www.youtube.com/watch?v=QUYCiIkzZlA  and add 7th command in #how to use# 