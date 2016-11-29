<tap-lane class="lane pure-u-1-5">

    <div class="bar" each="{w in words}" id="w-{ w.id }">{w.text}</div>

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
     me.mixin(Game.Lane);
     me.name = me.opts.label;

     me.on('mount',function() {
         // store
         me.words = [];
     });

    </script>

</tap-lane>
