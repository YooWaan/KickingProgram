

function Xhr(settings) {
    return Rx.Observable.ajax(settings);
}

function XhrToPromise(settings) {
    return Xhr(settings).toPromise();
}

function xhrSub(msgs, sub) {
    var p = undefined;
    msgs.forEach(function(m) {
        if (p == undefined) {
            p = XhrToPromise(m);
        }
        p = p.then(function(data) {
            m.success(data);
            return XhrToPromise(m)
        });
    });
    return p;
    /*
    Rx.Observable.from(msgs)
        .map(function(m) {
            return Xhr(m).subscribe(m.success);
        })
        .subscribe(sub,
                  function(err){},
                  function(){});
    */
}

module.exports = {
    xhrSubscribe : xhrSub,
    xhrPromise : XhrToPromise,
    xhrRequest : Xhr
}
