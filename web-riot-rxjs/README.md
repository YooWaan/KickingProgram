# Readme


## Environment Setup

```
npm init
npm install webpack --save-dev
```


## Commands


```
# run webpack
npm run pack

# start web server
npm run dev
```

## Common Modules

* seq.js : interval message word generator
* anime  : animation utility
* web.js : xhr utility

## Flux

* action creator
   * UI Event handler
       * lane tap
       * start/stop tap
* dispatcher
   * riotjs observable 
* callback , store, change event+store queries functions
   * animation execute
   * score count up

## Clean Architecture

```
 [ UI(Riotjs) ]    [ API ]  : External IF
    |                 |
 [ Presenter ]    [ Ctrl ]  : IF Adapter
    \               /
 [Usecase:IN      OUT]      : Usecase
      \ Intractor /
          \ IN  (Ovservable)
            /
   [ Entities ]             : Model, Modules
```

## Reactive Program

```

UI Tags       [==================]
                   A  A    A
* event            |  |    |
   start/stop -x---)--)----)------
               |   |  |    |
   lane tap   -)---)--x----)------
               V   |       |
* sequencer    o   |       |
               V   |       |
   sequencer  -x-x-)-----x-)------
                 V |     V |
   anime      ---x-x-----x-x------

```
