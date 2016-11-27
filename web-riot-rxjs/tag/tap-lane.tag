<tap-lane class="lane pure-u-1-5">

    <div class="bar" each="{w in words}" id="w-{ w.id }"></div>

    <button class="tap button-secondary pure-button" onclick="{ onTap }" >{ opts.label }</button>


    <style>
     :scope .lane {
         background-color:#ccc;
         border:solid 1px gray;
         height:100%;
     }
     :scope .tap {
         position: absolute;
         bottom: 0px;
         width:inherit;
         height:80px;
         text-align:center;
         vertical-align:middle;
         z-index:500000;
     }
     :scope .bar {
         background-color:yellow;
         position:absolute;
         top:0px;
         width:inherit;
         height:24px;
     }
    </style>

    <script>
     var me = this;
     var count = 0;

     // store
     me.words = [];

     me.on('mount',function() {
         me.words = [];
     });

     attach(wd) {
         me.words.push(wd);
         me.update({words:me.words});
         var goal = window.innerHeight * 0.7;
         anime({
             targets: [document.getElementById('w-' + wd.id)],
             translateY: [0, goal],
             duration: 8000,
             complete:function() {
                 var idx = me.words.findIndex(function(w) {
                     return wd === w;
                 });
                 if (idx != -1) {
                     me.words.splice(idx, 1);
                 }
                 console.log("done =>" + me.words.length + " , " + wd.id);
                 me.update({words:me.words});
             }
         });
     }

     onTap() {
         count = count + 1;
         me.attach({id:count, text:'hoge'});
     }

    </script>

</tap-lane>
