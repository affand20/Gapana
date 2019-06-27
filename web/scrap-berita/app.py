from flask import Flask

from functional.views import DetikScrap
#initialize app 
app = Flask(__name__)
app.add_url_rule('/', view_func=DetikScrap.as_view('show'))

if __name__ == "__main__":
    app.run()
