
interface ToString {
    value(): string;
}

interface Var<T> extends ToString {
    // var(): T;  error func
};

interface Hex extends Var<number> {
    hex(): string;
}

interface ToCase extends Var<string> {
    upper(): string;
    lower(): string;
}



const Val = <T>(v: T): Var<T> => {
    return {
        value(): string {
            return `${v}`;
        },
        var(): T {
            return v;
        }
    } as Var<T>;
};


class Value<T> {
    val: T;
    constructor(v: T) {
        this.val = v;
    }

    value(): string {
        return `${this.val}`;
    }

    var(): T {
        return this.val;
    }
};


export class VInt extends Value<number> {
    constructor(v: number) {
        super(v);
    }
    hex():string {
        return `0x${this.val.toString(16)}`;
    }
};


export class VStr extends Value<string> {
    constructor(v: string) {
        super(v);
    }
    upper():string {
        return this.val.toUpperCase();
    }
    lower():string {
        return this.val.toLowerCase();
    }
};


const fn = (s: string):void => {
    console.log(s);
}


function printStr(lst: Array<ToString>): void {
    console.log(lst.map((e, i) => {
        return `[${i}] ${e.value()}`;
    }).join(','));
}


const vn: Var<number> = new Value<number>(100)
  , vs: Var<string> = new Value<string>('hello')
  , fvn: Var<number> = Val<number>(200)
  , fvs: Var<string> = Val<string>('HELO');

const vss: Value<string> = new Value('hello value')
const ii: VInt = new VInt(100);
const ss: VStr = new VStr('Hey');


const lst: Array<Value<string>> = new Array(vss);
const vlst: Array<Value<any>> = new Array(vss, ss);
const alst: Array<any> = new Array(vss, ii, ss, vn, vs, fvn);


const valst: Array<ToString> = new Array(vs, vn, fvn, fvs, vss, ii, ss);


printStr(lst);
printStr(vlst);
printStr(valst);



//fn(100);
/*
fn('Hello');
fn(vn.value());
fn(vs.value());
fn(fvn.value());
fn(fvs.value());

fn(ii.hex());
fn(ss.upper());
*/
//fn(vn);


/*
const sss: any = '100';
const iii: any = 100;
const fff: number = 100.0;

console.log(typeof (sss + iii));
console.log(typeof (iii + fff));
*/
