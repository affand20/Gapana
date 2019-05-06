from bs4 import BeautifulSoup
from selenium import webdriver
import selenium
import requests
from selenium.webdriver.chrome.options import Options
from models import models


def procced(item, url):
    if item == "kompas":
        pass

    if item == "detik":
        data = scrapt_selenium(url)
        get = getting_data_detik(data)
        return get

    if item == "jatimnet":
        pass


def scrapt_selenium(url):
    option = Options()
    option.add_argument('--headless')
    driver = webdriver.Chrome(options=option)
    driver.implicitly_wait(20)
    driver.get(url)
    bs = BeautifulSoup(driver.page_source, 'lxml')
    return bs


def scrap_requets(url):
    data = requests.get(url).text
    return BeautifulSoup(data, 'lxml')


def getting_data_detik(data):
    bs = data.find('div', {'class': 'list media_rows list-berita'})
    cs = bs.findAll('article')
    arr_news_source = []
    arr_title = []
    arr_date = []
    arr_desc = []
    arr_image = []
    for container in cs:
        arr_news_source.append(container.a['href'])
        arr_title.append(container.h2.text)
        arr_desc.append(container.p)
        arr_date.append(container.find('span',{'class':'date'}))
        arr_image.append(container.img['src'])
    return cs
