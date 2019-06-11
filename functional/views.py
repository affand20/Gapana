from bs4 import BeautifulSoup
import requests
from flask import Flask, redirect, request
from google.cloud import firestore
from flask.views import MethodView
import json
from selenium.webdriver.chrome.options import Options
from selenium import webdriver

app = Flask(__name__)
db  = firestore.Client()


class DetikScrap(MethodView):
    def scrap_detik(self):
        keyword = request.args.get("keywords","")
        if not keyword:
            return 
        url = "https://www.detik.com/search/searchall?query="
        urls = url + keyword
        fix = urls.replace(" ", "%20")
        data = requests.get(fix).text
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
                u'source': "Detik.com"
            })
        return redirect('/kompas?keywords='+request.args.get("keywords",""))

class KompasScrap(MethodView):
    def scrap_kompas(self):
        keyword = request.args.get("keywords","")
        if not keyword:
            return 
        url = "https://search.kompas.com/search/?q="
        urls = url + keyword
        fix = urls.replace(" ", "%20")
        option = Options()
        option.add_argument('--headless')
        driver = webdriver.Chrome(options=option)
        driver.get(fix)
        bs = BeautifulSoup(driver.page_source, "lxml")
        get_parsing = bs.find('div', {'class':'gsc-results gsc-webResult'})
        all_data = []
        first_data = get_parsing.find('div', {'class':'gsc-webResult gsc-result'})
        second_and_more = get_parsing.find('div',{'class':'gsc-expansionArea'})
        all_data.append(first_data)
        all_data.append(second_and_more)
        return all_data
    
    def parsing_data(self):
        data = self.scrap_kompas()
        urls = []
        titles = []
        image_urls = []
        descriptions = []
        for datas in data:
            if len(datas.find_all('div',{'class':'gsc-webResult gsc-result'})) == 0:
                url = datas.find('div', {'class':'gsc-thumbnail-inside'}).find('div',{'class':'gs-title'}).a['href']
                title = datas.find('div', {'class':'gsc-thumbnail-inside'}).find('div',{'class':'gs-title'}).a.text
                image_url = datas.find('div',{'class':'gsc-table-result'}).find('div',{'class':'gsc-table-cell-thumbnail gsc-thumbnail'}).find('div',{'class':'gs-image-box gs-web-image-box gs-web-image-box-landscape'}).find('a').img['src']
                description = datas.find('div',{'class':'gsc-table-result'}).find('div',{'class':'gs-bidi-start-align gs-snippet'}).text
                urls.append(url)
                titles.append(title)
                image_urls.append(image_url)
                descriptions.append(description)
            url = datas.find('div', {'class':'gsc-thumbnail-inside'}).find('div',{'class':'gs-title'}).a['href']
            title = datas.find('div', {'class':'gsc-thumbnail-inside'}).find('div',{'class':'gs-title'}).a.text
            image_url = datas.find('div',{'class':'gsc-table-result'}).find('div',{'class':'gsc-table-cell-thumbnail gsc-thumbnail'}).find('div',{'class':'gs-image-box gs-web-image-box gs-web-image-box-landscape'}).find('a').img['src']
            description = datas.find('div',{'class':'gsc-table-result'}).find('div',{'class':'gs-bidi-start-align gs-snippet'}).text
            urls.append(url)
            titles.append(title)
            image_urls.append(image_url)
            descriptions.append(description)
        return urls, titles, image_urls, descriptions
    
    def get(self):
        url, title, photo_url, description = self.parsing_data()
        for counter in range(len(title)):
            doc_ref = db.collection(u'news').document()
            doc_ref.set({
                u'url': url[counter],
                u'title': title[counter],
                u'photo_url': photo_url[counter],
                u'description': description[counter],
                u'source': "kompas"
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