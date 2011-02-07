export ANDROID_NDK_ROOT = /home/peterantoine/development/android-ndk-r5b
export TOOLROOT = $(ANDROID_NDK_ROOT)/toolchains/arm-eabi-4.4.0/prebuilt/linux-x86/bin/
export JDK_ROOT=/home/peterantoine/development/jdk1.6.0_22
export CLASSPATH=classes

# tool definitions
export CC = arm-eabi-gcc
export CCFLAGS = -fpic -mthumb-interwork -ffunction-sections -funwind-tables -fstack-protector -fno-short-enums \
				 -D__ARM_ARCH_5__ -D__ARM_ARCH_5T__ -D__ARM_ARCH_5E__ -D__ARM_ARCH_5TE__ -Wno-psabi -march=armv5te \
				 -mtune=xscale -msoft-float -mthumb -Os -fomit-frame-pointer -fno-strict-aliasing -finline-limit=64 -DANDROID -Wa,--noexecstack -O0 -g -c

export LINK = arm-eabi-gcc
export LINKFLAGS = -nostdlib -Wl,-shared,-Bsymbolic -Wl,--no-undefined -Wl,-z,noexecstack 

PACKAGE_TREE = classes/com/antoine/jni

# NOTE: Java depends is a HACK to get around java's need for a build order.
JAVA_DEPENDS = source/HNCP_JNI_GridItem.class
SOURCE_FILES = $(wildcard source/*.c)
JAVA_FILES   = $(wildcard source/*.java)
OBJECT_FILES = $(filter-out $(JAVA_DEPENDS),$(subst source,object,$(subst .c,$(TARGET_PLATFORM).o,$(SOURCE_FILES))))
CLASS_DEPENDS= $(subst source,$(PACKAGE_TREE),$(subst .java,.class,$(JAVA_DEPENDS)))
CLASS_FILES  = $(subst source,$(PACKAGE_TREE),$(subst .java,.class,$(JAVA_FILES)))

export LINKLIBS = $(ANDROID_NDK_ROOT)/toolchains/arm-eabi-4.4.0/prebuilt/linux-x86/lib/gcc/arm-eabi/4.4.0/libgcc.a \
				  $(ANDROID_NDK_ROOT)/platforms/android-8/arch-arm/usr/lib/libc.so \
				  $(ANDROID_NDK_ROOT)/platforms/android-8/arch-arm/usr/lib/libstdc++.so \
				  $(ANDROID_NDK_ROOT)/platforms/android-8/arch-arm/usr/lib/libm.so

export INCDIRS = -I$(ANDROID_NDK_ROOT)/platforms/android-8/arch-arm/usr/include 

PATH := $(PATH):$(TOOLROOT)

build : java_adapter.jar libjava_adapter.so

libjava_adapter.so : $(OBJECT_FILES)
	@$(LINK) $(LINKFLAGS) -o $(@) $(OBJECT_FILES) -Wl,--whole-archive $(BUILT_LIBS) -Wl,--no-whole-archive $(LINKLIBS)

java_adapter.jar : $(CLASS_DEPENDS) $(CLASS_FILES)
	@jar cvf $(@) -C classes .

$(PACKAGE_TREE)/%.class : source/%.java
	@javac $< -d classes

object/%.o : source/%.c include/com_antoine_jni_java_adapter.h
	@$(CC) $(CCFLAGS) -c -o $(@) $< -I $(JDK_ROOT)/include -I $(JDK_ROOT)/include/linux -I include $(INCDIRS)

include/com_antoine_jni_java_adapter.h : $(CLASS_FILES)
	@javah -o $(@) com.antoine.jni.java_adapter

clean:
	-@rm -rf object/*
	-@rm libjava_adapter.so
	-@rm java_adapter.jar

