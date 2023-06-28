package dev.necr.tweaker.loader;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class TweakerLoader implements PluginLoader {

    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();

        resolver.addRepository(new RemoteRepository.Builder("maven-central", "default", "https://repo.maven.apache.org/maven2/").build());
        resolver.addRepository(new RemoteRepository.Builder("sonatype", "default", "https://oss.sonatype.org/content/repositories/snapshots/").build());

        resolver.addDependency(new Dependency(new DefaultArtifact("cloud.commandframework:cloud-paper:1.8.3"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("cloud.commandframework:cloud-core:1.8.3"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("cloud.commandframework:cloud-annotations:1.8.3"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("cloud.commandframework:cloud-minecraft-extras:1.8.3"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("net.kyori:adventure-platform-bukkit:4.3.0"), null));

        classpathBuilder.addLibrary(resolver);
    }
}
