package auth;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import auth.handler.ConnectHandler;
import data.input.GenericSeekableLittleEndianAccessor;
import data.input.SeekableByteArrayStream;
import data.input.SeekableLittleEndianAccessor;

public class AuthServerHandler extends IoHandlerAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthServerHandler.class);

	@Override
	public final void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		LOGGER.error(cause.getLocalizedMessage(), cause);
	}

	@Override
	public final void inputClosed(IoSession session) throws Exception {
		super.inputClosed(session);
	}

	@Override
	public final void messageReceived(IoSession session, Object msg) throws Exception {
		if (!(msg instanceof byte[])) {
			return; // wtf
		}
		SeekableLittleEndianAccessor slea = new GenericSeekableLittleEndianAccessor(new SeekableByteArrayStream((byte[]) msg));
		byte b;
		switch (b = slea.readByte()) {
			case 0: {
				new ConnectHandler().handlePacket(session, slea);
				break;
			}
			default: {
				LOGGER.info("Unhandled. Header: {}", b);
				break;
			}
		}
		//LOGGER.info("Received: {}", HexTool.toString((byte[]) msg));
	}

	@Override
	public final void messageSent(IoSession session, Object msg) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public final void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public final void sessionCreated(IoSession session) throws Exception {
		super.sessionClosed(session);
	}

	@Override
	public final void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public final void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}
}