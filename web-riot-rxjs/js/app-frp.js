// define requires
var Seq = require('./seq.js').Sequencer;
var An = require('./anime.js');
var Web = require('./web.js');


// initialize
(function(win){

    // observable
    var initializeSource = new Rx.Subject()
        .map(function(request){
            return Web.xhrPromise(request);
        });
    var animeSource = new Rx.Subject();
    var scoreupSource = new Rx.Subject();

    var uiEventSource = new Rx.Subject();
    var sequenceSource = new Rx.Subject();
    var sequencer = new Seq(function(wd) {
        sequenceSource.next(wd);
    });

    // reactice functions
    var animeSubscribe = function(msg) {
        var me = msg.lane;
        var wd = msg.word;
        // store
        me.words.push(wd);
        me.update({words:me.words});
        An(me, wd);
    };
    animeSource.subscribe(animeSubscribe)

    uiEventSource
        .map(function(flg){
            return flg ? sequencer.start() : sequencer.stop();
        })
        .subscribe(function(result){
            if (!result) {
                alert('already done');
            }
        });

    var mixin = {

        // panel presenter
        Panel : {
            initPanel : function() {
                // reactive functions
                // panel タグへの参照が必要なので、ここでいろいろ設定
                var panel = this;
                initializeSource
                    .subscribe(function(prm) {
                        prm.then(function(res) {
                            var cfg = res.response;
                            panel.configure(cfg);
                            console.log(cfg);
                            sequencer.reset(cfg);
                        });
                    });

                sequenceSource
                    .subscribe(function(wd) {
                        var tag = panel.findLane(wd.lane);
                        if (tag) {
                            tag.attach(wd);
                        }
                    });

                scoreupSource
                    .filter(function(score){
                        return score.words.length > 0
                            && 85 <= score.words[0].Ypos && score.words[0].Ypos <= 95
                    })
                    .map(function(score) {
                        return score.words[0].text;
                    })
                    .subscribe(function(w){
                        score.inc(w);
                    });

                initializeSource.next({url:'/conf.json', responseType:'json'});
            }
        },

        //
        // subscribers
        //

        // lane presenter
        Lane : {
            attach : function(wd) {
                animeSource.next({lane:this, word:wd});
            },

            onTap : function(e) {
                scoreupSource.next(this);
            }
        },

        // score presenter
        Score : {
            onStart : function() {
                uiEventSource.next(true);
            },

            onStop : function() {
                uiEventSource.next(false);
            },

            onReset : function() {
                initializeSource.next({url:'/conf.json', responseType:'json'});
            }
        }
    };

    win.Game = mixin;

})(window);
