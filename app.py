import flask
from flask_api import FlaskAPI, status, exceptions
from flask import request, url_for, Flask
from scrapt import scrapt
from models.models import db, list_news_url
from scrapt.scrapt import procced

app = Flask(__name__)


# begin news
@app.route('/news')
def news_list():
    data = db.collection('news')
    rest = data.get()
    list_id = {}
    for rests in rest:
        list_id[rests.id] = rests.to_dict()
    return flask.jsonify(
        {'id': list_id}
    )


# end news

@app.route('/scrap_news')
def scrap_auto():
    url = list_news_url()
    for i, k in url.items():
        a = procced(i, k)
        print(a)
    # doc_ref = db.collection('news').document('news_kompas')
    # doc_ref.set({
    #     'image': 'image',
    #     'news_source': '',
    #     'subtitle': '',
    #     'title': '',
    #     'tag': '',
    #     'teaser': '',
    #     'url' : '',
    # })
    return "AAAAA"


# begin user

if __name__ == '__main__':
    app.run()
