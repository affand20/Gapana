import firebase_admin
from firebase_admin import db, firestore

from firebase_admin import credentials

cred = credentials.Certificate("gapana-3283b-firebase-adminsdk-z8698-23b44628d6.json")

firebase_admin.initialize_app(cred,options={
    'databaseURL': 'https://gapana-3283b.firebaseio.com'
})
NEWS = db.reference('news')
USER = db.reference('user')

def list_news_url():
    data = {
        'detik':'https://www.detik.com/search/searchall?query=bencana',
        'kompas':'https://search.kompas.com/search/?q=',
        'jatimnet':'http://api.jatimnet.com/jinetapi/news?search=',
        'jawapos' : 'https://www.jawapos.com/?s=',
        'tribun' : 'http://www.tribunnews.com/search?q=',
    }
    return data

db = firestore.client()