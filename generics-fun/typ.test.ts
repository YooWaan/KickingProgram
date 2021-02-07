import {ToString, Value, VInt, VStr, VNum, Point2, Point3} from './typ';


describe('type check', (): void => {

    test('test_value', (): void => {
        const vb :Value<Boolean> = new Value<Boolean>(true);
        const vs :VStr = new VStr('Test');
        const vi :VInt = new VInt(123);

        expect(vs.value()).toBe('Test');
        expect(vi.var()).toBe(123);
        expect(vb.var()).toBe(true);

        // duck typing
        const tostr: ToString = vb;
        expect(vb.value()).toBe('true');

        // dynamic typing
        let val = '1000';
        expect(val).toBe('1000');
        // @ts-ignore:
        val = 123;
        expect(val).toBe(123);

        // struct typeing ?
        let p2: Point2 = {x:1, y:2} as Point2;
        let p3: Point3 = {x:4, y:5, z:6} as Point3;

        expect(p2.x).toBe(1);
        //console.log(p2); console.log(p3);

        // p3 = p2; // can not
        p2 = p3;
        expect(p2.x).toBe(4);
    });

    test('invarient', (): void => {
        const vb :Value<Boolean> = new Value<Boolean>(true);
        const vs :VStr = new VStr('Test');
        const vi :VInt = new VInt(123);

        const lst: Array<Value<any>> = [vb, vs, vi];
        console.log(lst);
        expect(lst.length).toBe(3);

        const slst: Array<Value<Boolean>|VStr|VInt> = [vs, vi];
        console.log(slst);
        expect(slst.length).toBe(2);

        // need ts-ignore
        //const vlst: Array<Value<VStr|VInt>> = slst; // @ts-ignore:
        //console.log(vlst);
        //expect(vlst.length).toBe(2);
    });

    test('co_contra_varient', (): void => {
        const vb :Value<Boolean> = new Value<Boolean>(true);
        const vs :VStr = new VStr('Test');
        const vi :VInt = new VInt(123);
        const vn :VNum = new VNum(10);

        //const toList:  <T extends VStr>(v:Array<T>): Array<T> = <T extends VStr>(v: Array<T>) => {return v;};
        const toList = function<T extends VStr>(v: Array<T>): Array<T> {
            return v;
        };

        const lst: Array<VStr> = toList([vn, vs]);
        lst.push(vn);
        lst.push(vs);
        //lst.push(vi);
        //lst.push(vb);
        console.log(lst);
        expect(lst.length).toBe(4);

        const alst: Array<VStr> = [vn, vs];
        alst.push(vn); alst.push(vs);
        //alst.push(vi); alst.push(vb);
        console.log(alst);

        const olst: Array<Object> = alst;
        olst.push(vn); olst.push(vs);
        olst.push(vi); olst.push(vb);
        console.log(olst);
        console.log(alst);
        expect(olst.length).toBe(8);
        expect(alst.length).toBe(8);
    });
});