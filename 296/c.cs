using System;
using System.Linq;
using System.Collections.Generic;

#if ONLINE_JUDGE

#endif

namespace codeforces
{
		public class MainClass
		{
				public const string QuestName = "GlassCarving";
				public const string QuestLink = "http://codeforces.ru/problemset/problem/527/c";

				public static void Main()
				{
						var solver = new MainClass();
						solver.Solve();
				}

				SegmentManager V, H;
				string[] input;
				int w, h, n;
				Segment s;
				public void Solve()
				{
						V = new SegmentManager();
						H = new SegmentManager();
						input = Console.ReadLine().Split(' ');
						w = int.Parse(input[0]);
						h = int.Parse(input[1]);
						n = int.Parse(input[2]);

						s = new Segment(0, h);
						V.Add(s);
						s = new Segment(0, w);
						H.Add(s);

						for (int step = 0; step < n; ++step)
						{
								input = Console.ReadLine().Split(' ');
								var sm = input[0] == "V" ? H : V;
								sm.Slice(int.Parse(input[1]));
								Console.WriteLine(V.MaxWidth * H.MaxWidth);
						}

				}
				public class Segment
				{
						public Segment(int x1, int x2)
						{
								X1 = x1;
								X2 = x2;
						}

						public int X1, X2;
						public int Width { get { return X2 - X1; }}

						public override string ToString()
						{
								return String.Format("[X1={0} X2={1} Width={2}]", X1, X2, Width);
						}
				}

				public class SegmentManager
				{
						TreeSet<Segment>
								PositionOrdered = new TreeSet<Segment>(new SegmentPositionComparer()),
								WidthOrdered = new TreeSet<Segment>(new SegmentWidthComparer());

						public void Add(Segment s)
						{
								PositionOrdered.Add(s);
								WidthOrdered.Add(s);
						}
						public void Remove(Segment s)
						{
								PositionOrdered.Remove(s);
								WidthOrdered.Remove(s);
						}
						public Segment Find(int x)
						{
								var anonymous = new Segment(x, x);
								var s = PositionOrdered.FindFloor(anonymous);
#if DEBUG
								Console.WriteLine("({0})", s.X1);
#endif
								return s;
						}
						public void Slice(int x)
						{
								Segment s = Find(x);
								Remove(s);
#if DEBUG
								Console.WriteLine(s.ToString());
#endif
								var s1 = new Segment(s.X1, x);
								var s2 = new Segment(x, s.X2);
								Add(s1);
								Add(s2);
						}
						public int MaxWidth
						{
								get
								{
										return WidthOrdered.Max.Width;
								}
						}

				}

				public class SegmentWidthComparer : IComparer<Segment>
				{
						public int Compare(Segment s1, Segment s2)
						{
								int order = s1.Width.CompareTo(s2.Width);
								return order == 0 ? s1.X1.CompareTo(s2.X1) : order;
						}
				}

				public class SegmentPositionComparer : IComparer<Segment>
				{
						public int Compare(Segment s1, Segment s2)
						{
								int order = s1.X1.CompareTo(s2.X1);
								return order == 0 ? s1.Width.CompareTo(s2.Width) : order;
						}
				}
				public class TreeSet<T> : SortedSet<T>
				{
						public TreeSet() : this (Comparer<T>.Default) { }
						public TreeSet(IComparer<T> cmp) : this (new Cmp(cmp, default(T))) { }
						public TreeSet(Cmp cmp) : base (cmp) 
						{
								this.cmp = cmp;
						}

						private Cmp cmp;

						public T FindFloor(T t)
						{
								cmp.Reset();
								Contains(t);
								return cmp.Least;
						}

						public T FindCeil(T t)
						{
								cmp.Reset();
								Contains(t);
								return cmp.Largest;
						}

						public class Cmp : IComparer<T>
						{
								public Cmp (IComparer<T> cmp, T Default)
								{
										this.cmp = cmp;
										this.Default = Default;
								}

								private IComparer<T> cmp;
								private T Default;
								public T Least, Largest;
								public bool LeastSet = false, LargestSet = false;

								public int Compare(T t1, T t2)
								{
										int order = cmp.Compare(t1, t2);
										if (order >= 0 && (LeastSet = true))
												Least = t2;
										if (order <= 0 && (LargestSet = true))
												Largest = t2;
										return order;
								}

								public void Reset()
								{
										Least = Largest = Default;
										LeastSet = LargestSet = false;
								}
						}
				}

		}
}
