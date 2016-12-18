# Readme


## Environment Setup

```
npm init
npm install gulp --save-dev
npm install webpack --save-dev


```


## Commands


```
# run webpack
npm run pack

# start web server
npm run dev

```

## Flux

* action creator
* dispatcher
* callback , store, change event+store queries functions

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
