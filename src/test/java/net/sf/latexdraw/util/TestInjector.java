package net.sf.latexdraw.util;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Handler;
import net.sf.latexdraw.HelperTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

@RunWith(MockitoJUnitRunner.class)
public class TestInjector implements HelperTest {
	Injector injector;
	@Mock Handler handler;

	@Before
	public void setUp() {
		I.cpt = 0;
		Injector.LOGGER.addHandler(handler);
	}

	@After
	public void tearDown() {
		Injector.LOGGER.removeHandler(handler);
		if(injector != null) {
			injector.clear();
		}
	}

	@Test
	public void testConstructor() {
		new Injector() {
			@Override
			protected void configure() {
			}
		};
	}

	@Test
	public void testCreateRegisteredObject() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(D.class);
			}
		};

		assertNotNull(injector.getInstance(D.class));
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testFailClassWithNoDefaultConst() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(K.class);
			}
		};
		assertNull(injector.getInstance(K.class));
		Mockito.verify(handler, Mockito.atLeastOnce()).publish(Mockito.any());
	}

	@Test
	public void testFailOnNullClass() {
		injector = new Injector() {
			@Override
			protected void configure() {
			}
		};
		assertNull(injector.getInstance(null));
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testSingletonRegisteredObject() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(D.class);
			}
		};

		assertSame(injector.getInstance(D.class), injector.getInstance(D.class));
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testFailOnNonConfigParam() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(A.class);
			}
		};
		assertNotNull(injector.getInstance(A.class));
		assertNull(injector.getInstance(A.class).b);
		Mockito.verify(handler, Mockito.atLeastOnce()).publish(Mockito.any());
	}

	@Test
	public void testInjectOnDeclaredField() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(D.class);
				bindAsEagerSingleton(C.class);
			}
		};

		assertNotNull(injector.getInstance(C.class));
		assertNotNull(injector.getInstance(D.class));
		assertSame(injector.getInstance(D.class), injector.getInstance(C.class).d);
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testInjectOnInheritedField() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(D.class);
				bindAsEagerSingleton(E.class);
			}
		};

		assertNotNull(injector.getInstance(D.class));
		assertNotNull(injector.getInstance(E.class));
		assertSame(injector.getInstance(D.class), injector.getInstance(E.class).d);
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testInjectOnBindCmd() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(G.class);
				bindWithCommand(F.class, G.class, g -> g);
				bindAsEagerSingleton(H.class);
			}
		};

		assertNotNull(injector.getInstance(G.class));
		assertNotNull(injector.getInstance(F.class));
		assertNotNull(injector.getInstance(H.class));
		assertSame(injector.getInstance(F.class), injector.getInstance(H.class).f);
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testInjectOnCyclicDependency() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(A.class);
				bindAsEagerSingleton(B.class);
			}
		};

		assertNotNull(injector.getInstance(A.class));
		assertNotNull(injector.getInstance(B.class));
		assertSame(injector.getInstance(A.class), injector.getInstance(B.class).a);
		assertSame(injector.getInstance(B.class), injector.getInstance(A.class).b);
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testDoNotCreateSingletonSeveralTimes() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(I.class);
			}
		};

		injector.getInstance(I.class);
		injector.getInstance(I.class);
		assertEquals(1, I.cpt);
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testCanInjectItself() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(J.class);
			}
		};

		injector.getInstance(J.class);
		assertEquals(injector.getInstance(J.class), injector.getInstance(J.class).j);
		Mockito.verify(handler, Mockito.never()).publish(Mockito.any());
	}

	@Test
	public void testFailBindingOnOutClassAlreadyReg() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(C.class);
				bindAsEagerSingleton(E.class);
				bindWithCommand(C.class, E.class, obj -> obj);
			}
		};

		assertNotNull(injector.getInstance(C.class));
		assertNotNull(injector.getInstance(E.class));
		Mockito.verify(handler, Mockito.atLeastOnce()).publish(Mockito.any());
	}

	@Test
	public void testFailBindingOnSrcClassNotReg() {
		injector = new Injector() {
			@Override
			protected void configure() {
				bindWithCommand(C.class, E.class, obj -> obj);
			}
		};

		Mockito.verify(handler, Mockito.atLeastOnce()).publish(Mockito.any());
	}

	@Test
	public void testGetInstanceNotNull() {
		injector = new Injector() {
			@Override
			protected void configure() {
			}
		};

		assertNotNull(injector.getInstances());
	}

	@Test
	public void testGetInstanceOK() {
		injector = new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(D.class);
			}
		};
		final D obj = injector.getInstance(D.class);
		assertEquals(obj, injector.getInstances().iterator().next());
	}

	static class A {
		@Inject B b;
	}

	static class B {
		@Inject A a;
	}

	static class C {
		@Inject D d;
	}

	static class D {
	}

	static class E extends C {

	}

	interface F {
	}

	static class G implements F {

	}

	static class H {
		@Inject F f;
	}

	static class I {
		static int cpt = 0;

		public I() {
			cpt++;
		}
	}

	static class J {
		@Inject J j;
	}

	static final class K {
		private K() {

		}
	}
}
