import os
from setuptools import setup, find_packages

def read(fname):
    return open(os.path.join(os.path.dirname(__file__), fname)).read()

setup(
    name='ut-sample',
    version='0.0.0',
    description='A sample Python unit test project',
    long_description=read('README'),
    url='https://github.com/WooYooWaan/KickingProgram/ut',
    author='The Python Packaging Authority',
    author_email='ut@test.com',
    license='MIT',
    classifiers=[
        'Development Status :: 3 - Alpha',
        'Intended Audience :: Developers',
        'Topic :: Software Development :: Build Tools',
        'License :: OSI Approved :: MIT License',
        'Programming Language :: Python :: 2.7'
    ],
    keywords='sample setuptools development unittest',
    packages = find_packages(exclude=['docs', 'tests*']),
    test_suite="tests",


    #install_requires=['peppercorn'],
    #extras_require={},
    #package_data={'sample': ['package_data.dat'],},
    #data_files=[('my_data', ['data/data_file'])],
    #entry_points={
    #    'console_scripts': [
    #        'sample=sample:main',
    #    ],
    #},
)
