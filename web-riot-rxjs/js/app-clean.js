// define requires
var Seq = require('./seq.js').Sequencer;
var An = require('./anime.js');
var Web = require('./web.js');

// architecture dependecies
var Usecase = require('./clean/usecase.js').Usecase;

// initialize
(function(win){

    // Usecases
    var seqUse   = new Usecase();
    var panelUse = new Usecase();
    var laneUse  = new Usecase();
    var scoreUse = new Usecase();

    // Entity
    var seqEntity = new Seq(function(wd) {
        seqUse.input('on-word-attach', wd);
    });

    var animeEntity = function(msg) {
        var me = msg.lane;
        var wd = msg.word;
        // store
        me.words.push(wd);
        me.update({words:me.words});
        An(me, wd);
    };

    //
    // external(IF)
    //
    var init = function() {
        // gateway IF
        Web.xhrPromise({url:'/conf.json', responseType:'json'})
            .then(function(json) {
                panelUse.input('app-configure',json.response);
            });
    };

    //
    // usecase for entity
    //
    laneUse.output('start-anime', animeEntity);

    scoreUse.output('tap-onstart', function() {
        if (!seqEntity.start()) {
            alert('already done');
        }
    });

    scoreUse.output('tap-onstop', function() {
        if (!seqEntity.stop()) {
            alert('already done');
        }
    });

    //
    // IF Adapters
    //

    // presenter
    var mixin = {

        // panel presenter
        Panel : {
            initPanel : function() {
                panelUse.input('app-init', this);
            }
        },

        // lane presenter
        Lane : {
            attach : function(wd) {
                laneUse.input('start-anime', {lane:this, word:wd});
            },

            onTap : function(e) {
                if (this.words.length > 0
                   && 85 <= this.words[0].Ypos && this.words[0].Ypos <= 95) {
                    var wd = this.words[0];
                    laneUse.input('score-up', wd.text);
                }
            }
        },

        // score presenter
        Score : {
            onStart : function() {
                scoreUse.input('tap-onstart');
            },

            onStop : function() {
                scoreUse.input('tap-onstop');
            },

            onReset : function() {
                panelUse.input('app-configure', this.parent);
            }
        }
    };

    // controller (IF)
    panelUse.output('app-init', function(panel) {

        var controller = {
            attach : function(wd) {
                var tag = panel.findLane(wd.lane);
                if (tag) {
                    tag.attach(wd);
                }
            },
            scoreUp : function(w) {
                panel.tags['tap-score'].inc(w);
            },
            configure : function(cfg) {
                panel.configure(cfg);
                // TODO use seqUse
                seqEntity.reset(cfg);
            }
        };

        // controller IF mapping
        seqUse.output('on-word-attach', controller.attach);
        laneUse.output('score-up', controller.scoreUp);
        panelUse.output('app-configure', controller.configure);

        init(panel);
    });

    win.Game = mixin;

})(window);
