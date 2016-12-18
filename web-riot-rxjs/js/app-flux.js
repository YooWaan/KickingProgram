// define requires
var Seq = require('./seq.js').Sequencer;
var An = require('./anime.js');
var Web = require('./web.js');

// architecture dependecies
var Dp = require('./flux/dispatcher.js').Dispatcher;

// initialize
(function(win){

    // flux modules
    var dispatcher = new Dp();

    // game modules, action creator
    win.onkeydown = function() {
        
    };

    var mixin = {
        // panel action creator, action
        Panel : {
            initPanel : function() {
                // web request
                dispatcher.dispatch('app-init', this);
            }
        },

        // lane action creator
        Lane : {
            attach : function(wd) {
                dispatcher.dispatch('start-anime', { lane:this, word:wd});
            },

            onTap : function(e) {
                if (this.words.length > 0
                   && 85 <= this.words[0].Ypos && this.words[0].Ypos <= 95) {
                    var wd = this.words[0];
                    dispatcher.dispatch('score-up', wd.text);
                }
            }
        },

        // score action creator
        Score : {
            onStart : function() {
                dispatcher.dispatch('tap-onstart');
            },

            onStop : function() {
                dispatcher.dispatch('tap-onstop');
            },

            onReset : function() {
                dispatcher.dispatch('tap-onreset', this.parent);
            }
        }

    };

    var seq = new Seq(function(wd) {
        dispatcher.dispatch('on-word-attach', wd);
    });

    // game store, change event+store queries functions
    var init = function(me) {
        /*
        var config = {
            lanes : [
                {name:'A', key:'a'},
                {name:'S', key:'s'}
            ],
            words:['hoge', 'foo', 'bayoe'],
            seqCount: 10
        };*/
        Web.xhrPromise({url:'/conf.json', responseType:'json'})
            .then(function(json) {
                var config = json.response;
                me.configure(config);
                seq.reset(config);
            });
    };

    // register callback
    dispatcher.register('app-init', function(panel) {

        // word attach
        dispatcher.register('on-word-attach', function(wd) {
            var tag = panel.findLane(wd.lane);
            if (tag) {
                tag.attach(wd);
            }
        });

        // score inc
        dispatcher.register('score-up', function(w) {
            panel.tags['tap-score'].inc(w);
        });

        init(panel);
    });

    // score panel
    dispatcher.register('tap-onstart', function() {
        if (!seq.start()) {
            alert('already done');
        }
    });

    dispatcher.register('tap-onstop', function() {
        if (!seq.stop()) {
            alert('already done');
        }
    });


    dispatcher.register('tap-onreset', function(panel) {
        init(panel);
    });

    // lane callback
    dispatcher.register('start-anime', function(msg) {
        var me = msg.lane;
        var wd = msg.word;
        // store
        me.words.push(wd);
        me.update({words:me.words});
        An(me, wd);
    });

    win.Game = mixin;

})(window);

