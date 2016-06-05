Memo
==========


# sbt

### [Command Reference](http://www.scala-sbt.org/0.13/docs/Running.html)

* create project

<pre>
mkdir chX
cd chX
~/var/bin/create-sbt-prj chX
</pre>


* source build & run

<pre>
sbt clean

sbt clean compile

sbt run 
</pre>


### configuration


## chapter memo


### ch01

* ch01.TowersOfHanoi => Hanoi practice
* ch01.Curve => Curve practice


# gyp

### Command Reference

* create project

<pre>
{
    'targets': [
    {
        'target_name': 'a.out',
        'type': 'executable',
        'include_dirs': [
            'inc',
        ],
        'sources': [
            'src/main.cpp',
        ],
    },
  ],
}
</pre>


* source build & run

<pre>
#
mkdir -p <proj dir>/{inc,src}
cd <proj dir>
vi proj.gyp

# write source code
vi src/main.cpp

# generate build directory
GYP_GENERATORS=ninja gyp proj.gyp --toplevel-dir=. --depth=0

# build
cd out/Default
ninja

# execute 
./a.out

</pre>
