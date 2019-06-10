from bs4 import BeautifulSoup
import requests
from google.cloud import firestore
from flask.views import MethodView
import json

db  = firestore.Client()


class DetikScrap(MethodView):
    def scrap_detik(self):
        url = "https://www.detik.com/search/searchall?query=gempa%20bumi"
        data = requests.get(url).text
        filtering = BeautifulSoup(data,"lxml")
        get_parsing = filtering.find('div',{'class':'list media_rows list-berita'})
        parsed = get_parsing.find_all('article')
        return parsed

    def array_pass_data(self,data):
        url = data.a['href']
        title = data.h2.text
        photo_url = data.img['src']
        description = " "
        date = " "
        return url, title, photo_url, description, date
        
    def parsed_data(self):
        data = self.scrap_detik()
        urls = []
        titles = []
        photo_urls = []
        descriptions = []
        dates = []
        for datas in data:
            url,title, photo_url,description,date = self.array_pass_data(datas)
            urls.append(url)
            titles.append(title)
            photo_urls.append(photo_url)
            descriptions.append(description)
            dates.append(date)
        return urls, titles, photo_urls, descriptions, dates


    def get(self):
        url, title, photo_url, description, date = self.parsed_data()
        for counter in range(len(title)):
            doc_ref = db.collection(u'news').document(u'detik').collection(u'detik_news_bencana_gempa'+str(counter)).document()
            doc_ref.set({
                u'url': url[counter],
                u'title': title[counter],
                u'photo_url': photo_url[counter],
                u'description': description[counter],
                u'date': date[counter],
            })
        response = app.response_class(
            response = json.dumps({
                "status": "200",
                "response": "OK"
            }),
            status = 200, 
            mimetype = 'application/json'
        )
        return response


# @app.route('/kompas')
# def kompas_save():
#     doc_ref = db.collection(u'kompas').document(u'kompas')

# @app.route('/merdeka')
# def merdeka_save():
#     doc_ref = db.collection(u'merdeka').document(u'merdeka')

# @app.route('/jatimnet')
# def jatimnet_save():
#     doc_ref = db.collection(u'jatimnet').document(u'jatimnet')