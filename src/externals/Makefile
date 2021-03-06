SHELL := /bin/bash

ifndef $(prefix)
    prefix=$(shell cd $(CURDIR)/../.. && pwd)
endif

PYTHON=$(shell $(CURDIR)/../../release-tools/get-system-python)
OSID=$(shell $(CURDIR)/../../release-tools/getosid)

EXTDIR=$(prefix)/lib/pegasus/externals
LIBDIR=$(EXTDIR)/python

PACKAGES=sqlalchemy

ifneq ($(shell $(PYTHON) -V 2>&1 | grep -E 'ython 2\.[3-6]'),)
    PACKAGES+=ordereddict.py
endif

PACKAGES+=argparse
PACKAGES+=flask
PACKAGES+=flask_cache
PACKAGES+=flask_sqlalchemy.py
PACKAGES+=jinja2
PACKAGES+=markupsafe
PACKAGES+=wtforms
PACKAGES+=werkzeug
PACKAGES+=requests
PACKAGES+=itsdangerous.py
PACKAGES+=boto
PACKAGES+=pam.py
ifeq ($(shell $(CURDIR)/pyopenssl.sh),Yes)
    PACKAGES+=OpenSSL
endif
PACKAGES+=plex
ifeq ($(shell which pg_config),)
    $(warning WARNING: pg_config not found: skipping python postgresql library)
else
    PACKAGES+=psycopg2
endif
ifeq ($(shell which mysql_config),)
    $(warning WARNING: mysql_config not found: skipping python mysql library)
else
    PACKAGES+=MySQLdb
endif

all: $(addprefix $(LIBDIR)/,$(PACKAGES))

$(LIBDIR)/%:
	$(eval TARNAME := $(subst .tar.gz,,$<))
	@echo Building $< &&\
	mkdir -p $(LIBDIR) &&\
	tar xzf $< &&\
	pushd $(TARNAME) > /dev/null &&\
	$(PYTHON) setup.py install_lib -d $(LIBDIR) > /dev/null &&\
	popd > /dev/null &&\
	rm -rf $(TARNAME) &&\
	touch $@

$(LIBDIR)/argparse: argparse-1.4.0.tar.gz
$(LIBDIR)/sqlalchemy: SQLAlchemy-0.8.0.tar.gz
$(LIBDIR)/flask: Flask-0.10.tar.gz
$(LIBDIR)/flask_cache: Flask-Cache-0.13.1.tar.gz
$(LIBDIR)/flask_sqlalchemy.py: Flask-SQLAlchemy-0.16.tar.gz
$(LIBDIR)/jinja2: Jinja2-2.7.tar.gz
$(LIBDIR)/markupsafe: MarkupSafe-0.18.tar.gz
$(LIBDIR)/wtforms: WTForms-1.0.3.tar.gz
$(LIBDIR)/werkzeug: Werkzeug-0.9.3.tar.gz
$(LIBDIR)/requests: requests-2.8.0.tar.gz
$(LIBDIR)/itsdangerous.py: itsdangerous-0.21.tar.gz
$(LIBDIR)/boto: boto-2.38.0.tar.gz
$(LIBDIR)/pam.py: pam-0.1.4.tar.gz
ifeq ($(OSID),rhel5)
    # 0.13 does not work on rhel5
    $(LIBDIR)/OpenSSL: pyOpenSSL-0.12.tar.gz
else
    # 0.12 does not work on deb7 because of missing SSLv2_method
    $(LIBDIR)/OpenSSL: pyOpenSSL-0.13.tar.gz
endif
$(LIBDIR)/plex: plex-2.0.0dev.tar.gz
$(LIBDIR)/MySQLdb: MySQL-python-1.2.5.tar.gz
$(LIBDIR)/psycopg2: psycopg2-2.5.5.tar.gz
$(LIBDIR)/ordereddict.py: ordereddict-1.1.tar.gz

clean:
	rm -rf $(EXTDIR)

