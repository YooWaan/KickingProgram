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
         background-color:#ccc;
         border:solid 1px gray;
         height:100%;
     }
    </style>

    <script>

     initPanel() {
         me.config = {
             lanes : [
                 {name:'A', key:'a'},
                 {name:'S', key:'s'}
             ],
             words:['hoge', 'foo', 'bayoe']
         };
         me.update();
     }
     
     var me = this;
     this.on('mount', function(){
         me.config = {};
         me.initPanel();
     });


    </script>

</tap-panel>
