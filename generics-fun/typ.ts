
interface Var<T> {
    value(): string;
};

interface Hex {
    hex(): string;
}

interface ToCase {
    upper(): string;
    lower(): string;
}



const Val = <T>(v: T): Var<T> => {
    return {
        value(): string {
            return `${v}`;
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
};


class VInt extends Value<number> {
    constructor(v: number) {
        super(v);
    }
    hex():string {
        return `0x${this.val.toString(16)}`;
    }
};


class VStr extends Value<string> {
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

const vn: Var<number> = new Value(100);
const vs: Var<string> = new Value('hello');
const fvn: Var<number> = Val<number>(200);
const fvs: Var<string> = Val<string>('HELO');

const ii: VInt = new VInt(100);
const ss: VStr = new VStr('Hey');

//fn(100);
fn('Hello');
fn(vn.value());
fn(vs.value());
fn(fvn.value());
fn(fvs.value());

fn(ii.hex());
fn(ss.upper());

//fn(vn);


const sss: any = '100';
const iii: any = 100;
const fff: number = 100.0;

console.log(typeof (sss + iii));
console.log(typeof (iii + fff));