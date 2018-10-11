package net.sf.latexdraw.parsers.ps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPSIDivCommand extends TestPSCommand<PSIDivCommand> {
	@Override
	protected PSIDivCommand createCmd() {
		return new PSIDivCommand();
	}

	@Override
	@Test(expected = ArithmeticException.class)
	public void testExecuteVal0() throws InvalidFormatPSFunctionException {
		dequeue.push(10.0);
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
	}

	@Test(expected = ArithmeticException.class)
	public void testExecuteVal02() throws InvalidFormatPSFunctionException {
		dequeue.push(0.0);
		dequeue.push(0.0);
		cmd.execute(dequeue, 0.0);
	}

	@Override
	@Test
	public void testExecuteValNeg() throws InvalidFormatPSFunctionException {
		dequeue.push(-10.1);
		dequeue.push(-5.2);
		cmd.execute(dequeue, 0.0);
		assertEquals((int)(-10.1 / -5.2), dequeue.peek(), 0.00001);
	}

	@Override
	@Test
	public void testExecuteValPos() throws InvalidFormatPSFunctionException {
		dequeue.push(10.1);
		dequeue.push(5.3);
		cmd.execute(dequeue, 0.0);
		assertEquals((int)(10.1 / 5.3), dequeue.peek(), 0.00001);
	}

	@Override
	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize() throws InvalidFormatPSFunctionException {
		cmd.execute(dequeue, 0.0);
	}

	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testExecuteInvalidDequeueSize1() throws InvalidFormatPSFunctionException {
		dequeue.push(10.1);
		cmd.execute(dequeue, 0.0);
	}
}
