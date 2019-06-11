from flask import Flask

from functional.views import DetikScrap, KompasScrap, JatimnetScrap, MerdekaScrap, SuaraSurabayaScrap, TirtoScrap
#initialize app 
app = Flask(__name__)
app.add_url_rule('/', view_func=DetikScrap.as_view('show'))
app.add_url_rule('/kompas', view_func=KompasScrap.as_view('kompas'))
app.add_url_rule('/merdeka',view_func=MerdekaScrap.as_view('merdeka'))
app.add_url_rule('/jatimnet',view_func=JatimnetScrap.as_view('jatimnet'))
app.add_url_rule('/suarasurabaya', view_func=SuaraSurabayaScrap.as_view('suarasurabaya'))
app.add_url_rule('/tirto', view_func=TirtoScrap.as_view('tirto'))

if __name__ == "__main__":
    app.run()
