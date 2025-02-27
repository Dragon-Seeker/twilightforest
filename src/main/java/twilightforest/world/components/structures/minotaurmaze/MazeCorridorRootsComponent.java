package twilightforest.world.components.structures.minotaurmaze;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import twilightforest.world.registration.TFFeature;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class MazeCorridorRootsComponent extends MazeCorridorComponent {

	public MazeCorridorRootsComponent(ServerLevel level, CompoundTag nbt) {
		super(MinotaurMazePieces.TFMMCR, nbt);
	}

	public MazeCorridorRootsComponent(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(MinotaurMazePieces.TFMMCR, feature, i, x, y, z, rotation);
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		for (int x = 1; x < 5; x++) {
			for (int z = 0; z < 5; z++) {
				int freq = x;
				if (rand.nextInt(freq + 2) > 0) {
					int length = rand.nextInt(6);

					//place dirt above ceiling
					this.placeBlock(world, Blocks.DIRT.defaultBlockState(), x, 6, z, sbb);

					// roots
					for (int y = 6 - length; y < 6; y++) {
						this.placeBlock(world, TFBlocks.root_strand.get().defaultBlockState(), x, y, z, sbb);
					}

					// occasional gravel
					if (rand.nextInt(freq + 1) > 1) {
						this.placeBlock(world, Blocks.GRAVEL.defaultBlockState(), x, 1, z, sbb);

						if (rand.nextInt(freq + 1) > 1) {
							this.placeBlock(world, Blocks.GRAVEL.defaultBlockState(), x, 2, z, sbb);
						}
					}
				}
			}
		}
		return true;
	}
}
