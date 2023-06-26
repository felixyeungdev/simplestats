package com.williamneild;

import com.williamneild.http.FileRequestHandler;
import com.williamneild.http.HttpServer;
import com.williamneild.http.JsonDataRequestHandler;
import com.williamneild.http.RoutingRequestHandler;
import com.williamneild.suppliers.BulkSupplier;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class SimpleStats implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("simple-stats");

	private HttpServer webServer;

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric!");

		// create dir if not exists
		Path configDir = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), "simplestats");
		File configDirFile = new File(configDir.toUri());
		if (!configDirFile.exists()) configDirFile.mkdirs();

		// update web app if needed
		extractWebApp(Paths.get(String.valueOf(configDir), "web"));

		// init web server when server starts
		ServerLifecycleEvents.SERVER_STARTED.register(this::initialiseWebServer);
	}

	private void extractWebApp(Path webAppPath) {
		File webAppDir = new File(webAppPath.toUri());
		if (webAppDir.exists()) return;

		// create dir
		if (!webAppDir.mkdirs()) return;

		// get the web archive
		try {
			URL resource = getClass().getResource("/assets/simple-stats/web.zip");
			File tempFile = File.createTempFile("bluemap_webroot_extraction", null);
			if (resource == null) {
				LOGGER.error("Error getting web app zip");
				return;
			}

			FileUtils.copyURLToFile(resource, tempFile, 10000, 10000);
			try (ZipFile zipFile = new ZipFile(tempFile)) {
				Enumeration<? extends ZipEntry> entries = zipFile.entries();
				while (entries.hasMoreElements()) {
					ZipEntry zipEntry = entries.nextElement();
					if (zipEntry.isDirectory()) {
						File dir = webAppPath.resolve(zipEntry.getName()).toFile();
						FileUtils.forceMkdir(dir);
					} else {
						File target = webAppPath.resolve(zipEntry.getName()).toFile();
						FileUtils.forceMkdirParent(target);
						FileUtils.copyInputStreamToFile(zipFile.getInputStream(zipEntry), target);
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error("Error", e);
		}
	}

	private void initialiseWebServer(MinecraftServer minecraftServer) {
		String serverIp = minecraftServer.getServerIp();
		int serverPort = 25500;

		// create the request handler
		RoutingRequestHandler routingRequestHandler = new RoutingRequestHandler();

		// default route
		Path dir = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), "simplestats", "web");
		routingRequestHandler.register(".*", new FileRequestHandler(dir));

		routingRequestHandler.register("bulk", new JsonDataRequestHandler(new BulkSupplier(minecraftServer)));

		try {
			webServer = new HttpServer(routingRequestHandler);
			webServer.bind(new InetSocketAddress(
					serverIp, serverPort
			));
			webServer.start();
		} catch (UnknownHostException ex) {
			LOGGER.error("failed to resolve the ip.\n", ex);
		} catch (BindException ex) {
			LOGGER.error("failed to bind to the configured address.\n" +
					"This usually happens when the configured port (" + serverPort + ") is already in use by some other program.", ex);
		} catch (IOException ex) {
			LOGGER.error("failed to initialize the webserver.\n", ex);
		}
	}
}