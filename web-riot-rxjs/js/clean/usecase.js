
var Use = function() {
    this._ob = riot.observable(this);
};


Use.prototype.input = function(trigger, action) {
    var args = action;
    if (typeof action === 'function') {
        args = action();
    }
    this._ob.trigger(trigger, action);
};

Use.prototype.output = function(trigger, callback) {
    this._ob.on(trigger, callback);
};


module.exports = {
    Usecase: Use
};
