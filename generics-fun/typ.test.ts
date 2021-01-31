import {VStr} from './typ';


describe('type check', (): void => {

    test('hi', (): void => {

        const val: string = 'hello';
        expect(val).toBe('hello');

        const vs :VStr = new VStr('Test');
        expect(vs.value()).toBe('Test');
    });

});