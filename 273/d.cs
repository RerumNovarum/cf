using System;
using System.Linq;

#if ONLINE_JUDGE

#endif

namespace codeforces
{
		public class Solver
		{
				public const string QuestName = "RedGreenTowers";
				public const string QuestLink = "http://codeforces.ru/problemset/problem/478/d";

				public static void Main()
				{
						var input = Console.ReadLine().Split(' ');

						var solver = new Solver();
						solver.r = int.Parse(input[0]);
						solver.g = int.Parse(input[1]);
						solver.maxHeight = MaxHeight(solver.r + solver.g);
						solver.src = new int[solver.r + 1];
						solver.dst = new int[solver.r + 1];

						solver.Solve();
				}

				const int modulo = 1000*1000*1000 + 7;
				int r, g, maxHeight, reds, greens, ways;
				int[] src, dst; // src[r] = # of ways to build tower of current height, using 'r' red blocks

				public void Solve()
				{
						src[0] = 1;
						for (int h = 0; h < maxHeight; ++h)
						{
								int newLevelWidth = h + 1;

								for (reds = 0; reds <= r; ++reds)
								{
										ways = src[reds];
										if (ways == 0) continue;

										greens = CountGreenFromRed(reds, h);

#if DEBUG
										Console.WriteLine("h={0} reds={1} greens={2} ways={3} maxHeight={4}", h, reds, greens, ways, maxHeight);
#endif
										if (r - reds >= newLevelWidth)
												dst[reds + newLevelWidth] = (dst[reds + newLevelWidth] + ways) % modulo;
										if (g - greens >= newLevelWidth)
												dst[reds] = (dst[reds] + ways) % modulo;
								}

								NextGen();
						}


					  ways = SumByModulo(src);
						Console.WriteLine(ways);
				}

				public static int SumByModulo(int[] src)
				{
						int sum = 0;
						for (int i = 0; i < src.Length; ++i)
								sum = (sum + src[i]) % modulo;
						return sum;
				}

				private void NextGen()
				{
						dst.CopyTo(src, 0);
						for (int i = 0; i < dst.Length; ++i)
								dst[i] = 0;
				}
				public static int Count(int depth)
				{
						return depth * (depth + 1) / 2;
				}
				public static int CountGreenFromRed(int redCount, int depth)
				{
						int total = Count(depth);
						return total - redCount;
				}

				public static int MaxHeight(int number)
				{
						double
								n = number,
								d = Math.Sqrt(1 + 4*2*n),
								h = (-1 + d) / 2;
						return (int) Math.Floor(h);
				}
		}
}
