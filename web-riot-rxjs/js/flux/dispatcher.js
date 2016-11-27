

var DP = function() {
    this._ob = riot.observable(this);
};


DP.prototype.register = function(trigger, callback) {
    this._ob.on(trigger, callback);
};

DP.prototype.dispatch = function(trigger, action) {
    var args = action;
    if (typeof action === 'function') {
        args = action();
    }
    this._ob.trigger(trigger, action);
};


module.exports = {
    Dispatcher: DP
};
