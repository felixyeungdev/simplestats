package com.williamneild.http;

import java.nio.channels.SelectionKey;
import java.util.function.Consumer;

public interface SelectionConsumer extends Consumer<SelectionKey> {}