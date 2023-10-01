package elocindev.eldritch_end.worldgen.util;

import elocindev.eldritch_end.config.Configs;
import elocindev.eldritch_end.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

public class TreeFactory {
    // *------------------------*
    //        DEAD TREES
    // *------------------------*

    public static void addRandomMedium(StructureWorldAccess world, BlockPos pos, BlockState block) {
        if (world.getBlockState(pos.down()).getBlock() != BlockRegistry.ABYSMAL_FRONDS) return;
        
        switch (world.getRandom().nextInt(3)) {
            case 0:
                placeMediumDeadTree1(world, pos, block);
                break;
            case 1:
                placeMediumDeadTree2(world, pos, block);
                break;
            case 2:
                placeMediumDeadTree3(world, pos, block);
                break;
        }
    }

    // -- SMALL --
    public static void placeSmallDeadTree(StructureWorldAccess world, BlockPos pos, BlockState block) {
        if (world.getBlockState(pos.down()).getBlock() != BlockRegistry.ABYSMAL_FRONDS) return;
        
        for (int i = 0; i < 4; i++) {
            if (world.getRandom().nextBoolean() && i == 4) {
                world.setBlockState(pos.up(i), block, 3);
                break;
            }

            world.setBlockState(pos.up(i), block, 3);
        }

        world.setBlockState(randomSouthNorth(pos.up(world.getRandom().nextBetween(1, 2)), world), block.with(PillarBlock.AXIS, Direction.SOUTH.getAxis()), 3);
        if (world.getRandom().nextBoolean()) {
            world.setBlockState(randomEastWest(pos.up(world.getRandom().nextBetween(2, 3)), world), block.with(PillarBlock.AXIS, Direction.EAST.getAxis()), 3);
        }
    }

    // -- MEDIUM 1 --
    public static void placeMediumDeadTree1(StructureWorldAccess world, BlockPos pos, BlockState block) {
        if (world.getBlockState(pos.down()).getBlock() != BlockRegistry.ABYSMAL_FRONDS) return;

        BlockPos[] branches_vertical = {
            pos.south().up(4),
            pos.south().east().up(5),
            pos.south().east().up(6),
            pos.west(2).up(5),
            pos.west(2).up(6),
            pos.west(2).up(7),
            pos.north(2).east().up(3),
            pos.north(2).east().up(4)
        };        

        for (int i = 0; i < 4; i++) {
            world.setBlockState(pos.up(i), block, 3);
        }

        placeVerticalBranches(branches_vertical, world, block, BlockRegistry.PRIMORDIAL_LEAVES.getDefaultState());
        
        world.setBlockState(pos.north().up(2), block.with(PillarBlock.AXIS, Direction.NORTH.getAxis()), 3);
        world.setBlockState(pos.south().west().up(4), block.with(PillarBlock.AXIS, Direction.WEST.getAxis()), 3);
    }
    
    // -- MEDIUM 2 --
    public static void placeMediumDeadTree2(StructureWorldAccess world, BlockPos pos, BlockState block) {
        if (world.getBlockState(pos.down()).getBlock() != BlockRegistry.ABYSMAL_FRONDS) return;

        BlockPos[] branches_vertical = {
            pos.east().up(2),
            pos.west().up(2),
            pos.west().up(3),
            pos.west().up(4),
            pos.west().up(5),
            pos.west().up(6),
            pos.south(2).up(5),
            pos.south(2).up(6),
            pos.south(2).up(7),
            pos.south(2).up(8)
        };

        BlockPos[] branches_horizontal_ns = {
            pos.north().west().up(4),
            pos.south().west().up(4),
            pos.south(2).west().up(4)
        };

        BlockPos[] fix_leaves = {
            pos.north(2).west().up(4),
            pos.south(2).west(2).up(4),
            pos.south(2).up(4),
            pos.south(2).west().up(3),
            pos.south(3).west().up(4)
        };

        for (int i = 0; i < 2; i++) {
            world.setBlockState(pos.up(i), block, 3);
        }

        if (Configs.BIOME_PRIMORDIAL_ABYSS.enable_leaves_generation)
            for (int i = 0; i < fix_leaves.length; i++) {
                world.setBlockState(fix_leaves[i], BlockRegistry.PRIMORDIAL_LEAVES.getDefaultState(), 3);
            }

        placeVerticalBranches(branches_vertical, world, block, BlockRegistry.PRIMORDIAL_LEAVES.getDefaultState());

        for (int i = 0; i < branches_horizontal_ns.length; i++) {
            world.setBlockState(branches_horizontal_ns[i], block.with(PillarBlock.AXIS, Direction.SOUTH.getAxis()), 3);
        }
    }

    // -- MEDIUM 3 --
    public static void placeMediumDeadTree3(StructureWorldAccess world, BlockPos pos, BlockState block) {
        if (world.getBlockState(pos.down()).getBlock() != BlockRegistry.ABYSMAL_FRONDS) return;
        
        BlockPos[] branches_vertical = {
            pos.east().up(4),
            pos.east().up(5),
            pos.east().up(6),
            pos.east().up(7),
            pos.south(2).west(2).up(3),
            pos.south(2).west(2).up(4),
            pos.south(2).west(2).up(5),
            pos.north(2).west().up(2),
            pos.north(2).west().up(3)
        };

        BlockPos[] branches_horizontal_ns = {
            pos.north().up(1),
            pos.north().east().up(5)
        };

        BlockPos[] branches_horizontal_ew = {
            pos.south().west().up(2),
            pos.south(2).west(3).up(4)
        };

        for (int i = 0; i < 4; i++) {
            world.setBlockState(pos.up(i), block, 3);
        }

        placeVerticalBranches(branches_vertical, world, block, BlockRegistry.PRIMORDIAL_LEAVES.getDefaultState());
        
        for (int i = 0; i < branches_horizontal_ns.length; i++) {
            world.setBlockState(branches_horizontal_ns[i], block.with(PillarBlock.AXIS, Direction.SOUTH.getAxis()), 3);
        }

        for (int i = 0; i < branches_horizontal_ew.length; i++) {
            world.setBlockState(branches_horizontal_ew[i], block.with(PillarBlock.AXIS, Direction.EAST.getAxis()), 3);
        }
        
    }

    // *------------------------*
    //     BRANCH GENERATION
    // *------------------------*
    public static void placeVerticalBranches(BlockPos[] positions, StructureWorldAccess world, BlockState log, BlockState leaves) {        
        
        for (int i = 0; i < positions.length; i++) {
            world.setBlockState(positions[i], log, 3);
        }
        
        if ((Configs.BIOME_PRIMORDIAL_ABYSS.enable_leaves_generation)) {
            for (int i = 0; i < positions.length; i++) {
                if (world.getBlockState(positions[i].up()).isOf(log.getBlock())) {
                    for (int x = -1; x < 2; x++) {
                        for (int z = -1; z < 2; z++) {
                            if (world.getBlockState(positions[i].add(x, 0, z)).isAir()) {
                                world.setBlockState(positions[i].add(x, 0, z), leaves, 3);
                            }
                        }
                    }
                } else {
                    BlockPos[] pos = {positions[i].east(), positions[i].west(), positions[i].north(), positions[i].south(), positions[i].up(1) };
                    for (int j = 0; j < pos.length; j++)
                        world.setBlockState(pos[j], leaves, 3);
                }
                
            }
        }
    }

    // Utils

    public static BlockPos randomAny(BlockPos pos, StructureWorldAccess world) {
        switch (world.getRandom().nextInt(4)) {
            case 0:
                return pos.east();
            case 1:
                return pos.west();
            case 2:
                return pos.north();
            case 3:
                return pos.south();
        }

        return pos;
    }


    public static BlockPos randomSouthNorth(BlockPos pos, StructureWorldAccess world) {
        switch (world.getRandom().nextInt(2)) {
            case 0:
                return pos.north();
            case 1:
                return pos.south();
        }

        return pos;
    }

    public static BlockPos randomEastWest(BlockPos pos, StructureWorldAccess world) {
        switch (world.getRandom().nextInt(2)) {
            case 0:
                return pos.east();
            case 1:
                return pos.west();
        }

        return pos;
    }
}
