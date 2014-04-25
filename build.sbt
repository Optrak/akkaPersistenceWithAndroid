import android.Keys._, java.io.File

name := "akkaPersistenceWithAndroid"

organization := "com.optrak"

version := "1.0"

scalaVersion := "2.10.4"

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-language:_",
  "-encoding", "UTF-8"
)

android.Plugin.androidBuild

platformTarget in Android := "android-19"

targetSdkVersion in Android := 19

minSdkVersion in Android := 14

run <<= run in Android

install <<= install in Android

apkbuildExcludes in Android ++= Seq(
  "reference.conf",
  "META-INF/LICENSE.txt",
  "META-INF/mime.types",
  "LICENSE.txt",
  "META-INF/native/linux32/libleveldbjni.so",
  "META-INF/native/linux64/libleveldbjni.so",
  "META-INF/native/osx/libleveldbjni.jnilib",
  "META-INF/native/windows32/leveldbjni.dll",
  "META-INF/native/windows64/leveldbjni.dll",
  "org/fusesource/leveldbjni/version.txt"
)

dexMaxHeap in Android := "8192m"

useProguard in Android := true

proguardOptions in Android ++= Seq(
  "-dontobfuscate",
  "-dontoptimize",
  "-dontpreverify",
  "-ignorewarnings",
  // Scala stuff
  "-dontwarn scala.**",
  "-dontnote javax.xml.**",
  "-dontnote org.w3c.dom.**",
  "-dontnote org.xml.sax.**",
  "-dontnote scala.Enumeration",
  "-keep public class scala.Option",
  "-keep public class scala.Function0",
  "-keep public class scala.Function1",
  "-keep public class scala.Function2",
  "-keep public class scala.Product",
  "-keep public class scala.Tuple2",
  "-keep public class scala.collection.Seq",
  "-keep public class scala.collection.immutable.List",
  "-keep public class scala.collection.immutable.Map",
  "-keep public class scala.collection.immutable.Seq",
  "-keep public class scala.collection.immutable.Set",
  "-keep public class scala.collection.immutable.Vector",
  // Akka
  "-dontwarn sun.misc.Unsafe",
  "-dontwarn sun.reflect.Reflection",
  "-keep class com.typesafe.**",
  "-keep class akka.**",
  "-keep class scala.collection.immutable.StringLike { *; }",
  """|-keepclasseswithmembers class * {
    |    public <init>(java.lang.String, akka.actor.ActorSystem$Settings, akka.event.EventStream, akka.actor.Scheduler, akka.actor.DynamicAccess);
    |}""".stripMargin,
  """|-keepclasseswithmembers class * {
    |    public <init>(akka.actor.ExtendedActorSystem);
    |}""".stripMargin,
  """|-keep class scala.collection.SeqLike {
    |    public protected *;
    |}""".stripMargin,
  """|-keepclasseswithmembernames class * {
    |    native <methods>;
    |}""".stripMargin,
  """|-keepclasseswithmembers class * {
    |    public <init>(android.content.Context, android.util.AttributeSet);
    |}""".stripMargin,
  """|-keepclasseswithmembers class * {
    |    public <init>(android.content.Context, android.util.AttributeSet, int);
    |}""".stripMargin,
  """|-keepclassmembers class * extends android.app.Activity {
    |    public void *(android.view.View);
    |}""".stripMargin,
  """|-keepclassmembers enum * {
    |    public static **[] values();
    |    public static ** valueOf(java.lang.String);
    |}""".stripMargin,
  """|-keep class * implements android.os.Parcelable {
    |    public static final android.os.Parcelable$Creator *;
    |}""".stripMargin,
  """|-keepclasseswithmembers class * {
    |    public <init>(com.typesafe.config.Config, akka.event.LoggingAdapter, java.util.concurrent.ThreadFactory);
    |}""".stripMargin,
  """|-keepclasseswithmembers class * {
    |    public <init>(java.lang.String, akka.actor.ActorSystem$Settings, akka.event.EventStream, akka.actor.DynamicAccess);
    |}""".stripMargin,
  //
  "-keep class akka.persistence.**", //DeliveredByChannelBatching { *;} ",
  """|-keepclasseswithmembers class * {
    |    public <init>(akka.actor.ActorRef, akka.persistence.PersistenceSettings);
    |}""".stripMargin,
  "-keep class akka.persistence.DeliveredByChannelBatching { *;}",
  //
  "-keep class akka.actor.LocalActorRefProvider$Guardian { *; }",
  "-keep class akka.dispatch.UnboundedMailbox { *; }",
	"-keep public class akka.remote.RemoteActorRefProvider {public <init>(...);}",
	"-keep class akka.remote.RemoteActorRefProvider$RemotingTerminator {*;}",
  "-keep class akka.remote.transport.netty.NettyTransport {*;}",
  "-keep class akka.remote.transport.AkkaProtocolManager {*;}",
  "-keep class * extends akka.dispatch.RequiresMessageQueue {*;}",
  "-keep class akka.remote.transport.ProtocolStateActor$AssociationState {*;}",
  "-keep class akka.remote.PhiAccrualFailureDetector {*;}",
  "-keep class akka.remote.EndpointWriter {*;}",
  "-keep class akka.dispatch.UnboundedDequeBasedMailbox {*;}",
  "-keep class akka.remote.ReliableDeliverySupervisor {*;}",
  "-keep class akka.remote.RemoteWatcher {*;}",
  "-keep class akka.remote.EndpointManager {*;}",
  "-keep class akka.actor.CreatorFunctionConsumer {*;}",
  "-keep class akka.dispatch.BoundedDequeBasedMessageQueueSemantics {*;}",
  "-keep class akka.dispatch.UnboundedMessageQueueSemantics {*;}",
  "-keep class akka.dispatch.UnboundedDequeBasedMessageQueueSemantics {*;}",
  "-keep class akka.actor.LocalActorRefProvider$SystemGuardian {*;}",
  "-keep class * extends akka.remote.RemotingLifecycle",
  "-keep class akka.actor.DefaultSupervisorStrategy {*;}",
  "-keep class akka.remote.*",
  "-keep class scala.concurrent.duration.FiniteDuration",
  "-keep class scala.concurrent.ExecutionContext",
  "-keep class akka.actor.SerializedActorRef {*;}",
  "-keep class akka.actor.LightArrayRevolverScheduler {*;}",
  "-keep class akka.remote.netty.NettyRemoteTransport {*;}",
  "-keep class akka.serialization.JavaSerializer {*;}",
  "-keep class akka.serialization.ProtobufSerializer {*;}",
  "-keep class akka.serialization.ByteArraySerializer {*;}",
  "-keep class akka.event.Logging*",
  "-keep class akka.event.Logging$LogExt{*;}",
  "-keep class akka.io.TcpManager { *; }",
  "-keep class akka.routing.RoutedActorCell$RouterActorCreator { *; }"
)

proguardCache in Android ++= Seq(
  ProguardCache("akka") % "com.typesafe.akka" % "akka-actor_2.10",
  ProguardCache("akka.persistence") % "com.typesafe.akka" % "akka-persistence-experimental_2.10"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor_2.10" % "2.3.2",
  "org.mapdb" % "mapdb" % "0.9.13",
  "io.github.drexin" %% "akka-persistence-mapdb" % "0.1-SNAPSHOT",
  "com.typesafe.akka" % "akka-persistence-experimental_2.10" % "2.3.2"
)

externalDependencyClasspath in Compile ~= { cp => cp filterNot { attributed => attributed.data.getName contains "leveldbjni-all-1.7.jar"}}
