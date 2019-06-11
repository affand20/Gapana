from flask import Flask

from functional.views import DetikScrap, KompasScrap
#initialize app 
app = Flask(__name__)
app.add_url_rule('/', view_func=DetikScrap.as_view('show'))
app.add_url_rule('/kompas', view_func=KompasScrap.as_view('kompas'))

if __name__ == "__main__":
    app.run()
