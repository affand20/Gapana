from bs4 import BeautifulSoup
import requests
from flask import Flask
from google.cloud import firestore
from flask.views import MethodView
import json
app = Flask(__name__)
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
            doc_ref = db.collection(u'news').document()
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

class KompasScrap(MethodView):
    def scrap_merdeka():
        pass
    
    def get():
        pass

class MerdekaScrap(MethodView):
    def scrap_merdeka(self):
        pass
    
    def get(self):
        pass

class JatimnetScrap(MethodView):
    def get_api(self):
        pass
        
    def get_slug(self,data):
        pass
    
    def set_url(self):
        pass
    
    def get():
        pass

class SuaraSurabayaScrap(MethodView):
    def scrap_ss(self):
        pass
    
    def get(self):
        pass 
        
class TirtoScrap(MethodView):
    def scrap_tirto(self):
        pass
    
    def get(self):
        pass
        
