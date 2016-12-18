
function getRandomInt(min, max) {
    return Math.floor( Math.random() * (max - min + 1) ) + min;
}

var IntervalSeq = function(d) {
    this.lanes = [];
    this.words = [];
    this.count = 0;
    this.maxCount = 0;
    this.delegator = d;
}

IntervalSeq.prototype._isDone = function() {
    return this.count >= this.maxCount;
}

IntervalSeq.prototype._interval = function() {
    var me = this;
    return function() {
        //console.log('interval ->' + me.count + ', ' + me.maxCount);
        if (me._isDone()) {
            me.stop();
        } else {
            me.count = me.count + 1;
            var word = {
                id  :  me.count,
                text: me.words[ getRandomInt(0, me.words.length-1) ].w,
                lane: me.lanes[ getRandomInt(0, me.lanes.length-1) ]['name']
            };
            me.delegator(word);
        }
    };
}

IntervalSeq.prototype.reset = function(cfg) {
    if (this.intervalId !== undefined) {
        this.stop();
    }
    this.lanes = cfg.lanes;
    this.words = cfg.words;
    this.count = 0;
    this.maxCount = cfg.seqCount;
}

IntervalSeq.prototype.start = function() {
    if (this._isDone()) {
        return false;
    }
    this.intervalId = setInterval(this._interval(), 1500);
    return true;
}

IntervalSeq.prototype.stop = function() {
    if (this._isDone()) {
        return false;
    }
    clearInterval(this.intervalId);
    this.intervalId = undefined;
    return true;
}

module.exports = {
    Sequencer : IntervalSeq
};
