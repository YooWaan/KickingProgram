<tap-panel class="pane">

    <div class="pane pure-g">
        <tap-score words="{config.words}"></tap-score>
        <tap-lane each="{ i in config.lanes }" label="{ i.name }"></tap-lane>
    </div>

    <style>
     :scope .pane {
         height:100%;
     }
     :scope .lane {
         background-color:#efefef;
         border:solid 1px gray;
         height:100%;
     }
    </style>

    <script>
     var me = this;
     me.mixin(Game.Panel);

     this.on('mount', function(){
         me.config = {lanes:[], words:[]};
         me.initPanel(me);
     });

     findLane(name) {
         return me.tags['tap-lane'].find(function(t){
             return t.name === name;
         });
     }

     configure(conf) {
         me.update({config:conf});
         me.tags['tap-score'].configure(conf.words);
     }

    </script>

</tap-panel>
