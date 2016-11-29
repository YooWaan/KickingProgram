<tap-score class="pane pure-u-1-5">

    <div class="buttons">
        <button class="tap button-secondary pure-button" onclick="{ onStart }" >Start</button>
        <hr/>
        <button class="tap button-secondary pure-button" onclick="{ onStop }" >Stop</button>
        <hr/>
        <button class="tap button-secondary pure-button" onclick="{ onReset }" >Rest</button>
        <hr/>
    </div>

    <p each="{wd in words}">{ wd.w } : { wd.s }</p>

    <style>
     :scope .pane {
         height:inherit;
         background-color:#eee;
     }
     :scope .buttons {
         text-align:center;
     }
     :scope .tap {
         width:80%;
     }
    </style>

    <script>
     var me = this;
     me.mixin(Game.Score);

     me.on('mount', function() {
         me.words = [];
     });

     configure(words) {
         this.update({words:words});
     }

     inc(wd) {
         var word = this.words.find(function(w) {
             return w.w == wd;
         });
         if (word) {
             word.s = word.s + 1;
             me.update();
         }
     }
    
    </script>

</tap-score>
